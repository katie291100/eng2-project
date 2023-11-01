package uk.ac.york.eng2.videos.controllers;


import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import uk.ac.york.eng2.videos.domain.Hashtag;
import uk.ac.york.eng2.videos.domain.User;
import uk.ac.york.eng2.videos.domain.Video;
import uk.ac.york.eng2.videos.dto.HashtagDTO;
import uk.ac.york.eng2.videos.dto.VideoDTO;
import uk.ac.york.eng2.videos.events.HashtagProducer;
import uk.ac.york.eng2.videos.events.VideoProducer;
import uk.ac.york.eng2.videos.repositories.HashtagsRepository;
import uk.ac.york.eng2.videos.repositories.UsersRepository;
import uk.ac.york.eng2.videos.repositories.VideosRepository;

@Controller("/videos")
public class VideosController {
    @Inject
    private VideosRepository repo;
    @Inject
    private UsersRepository userRepo;
    @Inject
    private VideoProducer kafkaClient;
    @Inject
    private HashtagsRepository hashtagRepo;

    @Inject
    private HashtagProducer hashtagKafkaClient;

    @Transactional
    @Get("/")
    public Iterable<Video> listVideos() {
        return repo.findAll();
    }

    @Get("/hashtag/{hashtag}")
    public List<Video> listVideosByHashtag(String hashtag) {
        Hashtag hashtagRecord = hashtagRepo.findByName(hashtag).orElse(null);
        if (hashtagRecord == null){
            return null;
        }
        List<Video> videos = repo.findAll();
        videos.removeIf(video -> !video.getHashtags().stream().toList().contains(hashtagRecord));
        return videos;
    }

    @Get("/{id}") //TODO: make return 404 if not found?
    public Video getVideo(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Transactional
    @Post("/")
    public HttpResponse<Void> addVideo(@Body VideoDTO videoDetails) {
        System.out.println(videoDetails.getHashtags());

        Set<Hashtag> hashtags = new HashSet<>();
        Iterable<HashtagDTO> hashtagDTOs = videoDetails.getHashtags();
        if (hashtagDTOs != null){
            for (HashtagDTO hashtag : hashtagDTOs) {
                Hashtag hashtagEntity = hashtagRepo.findByName(hashtag.getName()).orElse(null);
                if (hashtagEntity == null) {
                    hashtagEntity = new Hashtag();
                    hashtagEntity.setName(hashtag.getName());
                    hashtagRepo.save(hashtagEntity);
                }
                hashtags.add(hashtagEntity);
            }
        }

        Video video = new Video();
        video.setTitle(videoDetails.getTitle());
        video.setHashtags(hashtags);
        User poster = userRepo.findById(videoDetails.getPostedBy()).orElse(null);

        if (poster ==  null){
            return HttpResponse.notFound();
        }

        video.setPostedBy(poster);
        repo.save(video);
        kafkaClient.postVideo(video.getPostedBy().getId(), video);
        return HttpResponse.created(URI.create("/Video/" + video.getId()));
    }

    @Transactional
    @Put("/{id}")
    public HttpResponse<Void> updateVideo(long id, @Body VideoDTO video) {
        Video videoRecord = repo.findById(id).orElse(null);

        if (videoRecord == null) {
            return HttpResponse.notFound();
        }
        if (video.getTitle() != null) {
            videoRecord.setTitle(video.getTitle());
        }
        if (video.getPostedBy() != null) {
            User poster = userRepo.findById(video.getPostedBy()).orElse(null);

            if (poster ==  null){
                return HttpResponse.notFound();
            }

            videoRecord.setPostedBy(poster);
        }
        repo.update(videoRecord);
        return HttpResponse.ok();
    }

    @Transactional
    @Put("/{id}/like/{userId}")
    public HttpResponse<Void> likeVideo(long id, long userId) {
        Video videoRecord = repo.findById(id).orElse(null);
        User userRecord = userRepo.findById(userId).orElse(null);

        if (videoRecord == null || userRecord == null) {
            return HttpResponse.notFound();
        }
        if (videoRecord.getTitle() != null) {
            videoRecord.setLikes(videoRecord.getLikes() + 1);
        }

        repo.update(videoRecord);
        kafkaClient.likeVideo(userId, videoRecord);

        videoRecord.getHashtags().stream().toList().forEach(hashtag -> {
            hashtagKafkaClient.likeHashtag(hashtag.getId(), hashtag);
        });
        return HttpResponse.ok();
    }


    @Transactional
    @Put("/{id}/dislike/{userId}")
    public HttpResponse<Void> dislikeVideo(long id, long userId) {
        Video videoRecord = repo.findById(id).orElse(null);
        User userRecord = userRepo.findById(userId).orElse(null);

        if (videoRecord == null || userRecord == null) {
            return HttpResponse.notFound();
        }
        if (videoRecord.getTitle() != null) {
            videoRecord.setDislikes(videoRecord.getDislikes() + 1);
        }

        repo.update(videoRecord);
        kafkaClient.dislikeVideo(userId, videoRecord);

        return HttpResponse.ok();
    }

    @Delete("/{id}")
    @Transactional
    public HttpResponse<Void> deleteVideo(long id) {
        Video videoRecord = repo.findById(id).orElse(null);

        if (videoRecord == null) {
            return HttpResponse.notFound();
        }

        repo.delete(videoRecord);
        return HttpResponse.ok();
    }
}