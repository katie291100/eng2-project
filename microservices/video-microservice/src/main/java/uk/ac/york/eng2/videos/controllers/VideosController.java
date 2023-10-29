package uk.ac.york.eng2.videos.controllers;


import java.net.URI;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import uk.ac.york.eng2.videos.domain.User;
import uk.ac.york.eng2.videos.domain.Video;
import uk.ac.york.eng2.videos.dto.VideoDTO;
import uk.ac.york.eng2.videos.events.VideoProducer;
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

    @Get("/")
    public Iterable<Video> list() {
        return repo.findAll();
    }

    @Get("/{id}")
    public Video getVideo(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Transactional
    @Post("/")
    public HttpResponse<Void> addVideo(@Body VideoDTO videoDetails) {
        Video video = new Video();
        video.setTitle(videoDetails.getTitle());
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
            System.out.println("titleset");
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
//
//    @Delete("/{id}")
//    @Transactional
//    public HttpResponse<Void> deleteVideo(long id) {
//        Book videoRecord = repo.findById(id).orElse(null);
//
//        if (videoRecord == null) {
//            return HttpResponse.notFound();
//        }
//
//        repo.delete(videoRecord);
//        return HttpResponse.ok();
//    }
}