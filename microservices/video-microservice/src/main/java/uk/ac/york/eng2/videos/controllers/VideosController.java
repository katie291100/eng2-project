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

public class VideosController implements VideoAbstract {
    @Inject
    private VideosRepository repo;
    @Inject
    private UsersRepository userRepo;
    @Inject
    private VideoProducer kafkaClient;
    @Inject
    private HashtagsRepository hashtagRepo;

    public List<Video> listVideos() {
        return repo.findAll();
    }

    @Get("/hashtag/{hashtag}")
    public Set<Video> listVideosByHashtag(String hashtag) {

        List<Hashtag> hashtagRecords = hashtagRepo.findByName(hashtag);
        if (hashtagRecords.isEmpty()){
            return null;
        }
        Hashtag hashtags = hashtagRecords.get(0);
        List<Video> videos = repo.findAll();
        videos.removeIf(video -> !video.getHashtags().stream().toList().contains(hashtags));
        return hashtags.getVideos();
    }

    @Get("/{id}")
    public Video getVideo(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Transactional
    @Post("/")
    public HttpResponse<Void> addVideo(@Body VideoDTO videoDetails) {
        Set<Hashtag> hashtags = new HashSet<>();
        Iterable<HashtagDTO> hashtagDTOs = videoDetails.getHashtags();

        if (videoDetails.getTitle() == null || videoDetails.getPostedBy() == null){
            return HttpResponse.badRequest();
        }
        if (hashtagDTOs != null){
            for (HashtagDTO hashtag : hashtagDTOs) {
                Hashtag hashtagEntity;

                try{
                    hashtagEntity = hashtagRepo.findByName(hashtag.getName()).get(0);
                }catch (Exception e){
                    hashtagEntity = null;
                }

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
//
//    @Transactional
//    @Put("/{id}")
//    public HttpResponse<Void> updateVideo(long id, @Body VideoDTO video) {
//        Video videoRecord = repo.findById(id).orElse(null);
//
//        if (videoRecord == null) {
//            return HttpResponse.notFound();
//        }
//        if (video.getTitle() != null) {
//            videoRecord.setTitle(video.getTitle());
//        }
//        if (video.getPostedBy() != null) {
//            User poster = userRepo.findById(video.getPostedBy()).orElse(null);
//
//            if (poster ==  null){
//                return HttpResponse.notFound();
//            }
//
//            videoRecord.setPostedBy(poster);
//        }
//        repo.update(videoRecord);
//        return HttpResponse.ok();
//    }

    @Transactional
    public HttpResponse<Void> likeVideo(Long videoId, Long userId) {
        Video videoRecord = repo.findById(videoId).orElse(null);
        User userRecord = userRepo.findById(userId).orElse(null);

        if (videoRecord == null || userRecord == null) {
            return HttpResponse.notFound();
        }
        if (videoRecord.getTitle() != null) {
            videoRecord.setLikes(videoRecord.getLikes() + 1);
        }

        repo.update(videoRecord);
        kafkaClient.likeVideo(userId, videoRecord);

        return HttpResponse.ok();
    }


    @Transactional
    @Put("/{id}/dislike/{userId}")
    public HttpResponse<Void> dislikeVideo(Long id, Long userId) {
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

//    @Delete("/{id}")
//    @Transactional
//    public HttpResponse<Void> deleteVideo(long id) {
//        Video videoRecord = repo.findById(id).orElse(null);
//
//        if (videoRecord == null) {
//            return HttpResponse.notFound();
//        }
//
//        repo.delete(videoRecord);
//        return HttpResponse.ok();
//    }
}