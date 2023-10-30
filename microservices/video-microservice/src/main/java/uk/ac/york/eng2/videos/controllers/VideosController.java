package uk.ac.york.eng2.videos.controllers;


import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import uk.ac.york.eng2.videos.domain.Hashtag;
import uk.ac.york.eng2.videos.domain.User;
import uk.ac.york.eng2.videos.domain.Video;
import uk.ac.york.eng2.videos.dto.HashtagDTO;
import uk.ac.york.eng2.videos.dto.VideoDTO;
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

    @Get("/")
    public Iterable<Video> listVideos() {
        return repo.findAll();
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
        for (HashtagDTO hashtag : videoDetails.getHashtags()) {
            System.out.println("Making hashtag");
            Hashtag hashtagEntity = hashtagRepo.findByName(hashtag.getName()).orElse(null);
            if (hashtagEntity == null) {
                hashtagEntity = new Hashtag();
                hashtagEntity.setName(hashtag.getName());
                hashtagRepo.save(hashtagEntity);
            }
            hashtags.add(hashtagEntity);
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