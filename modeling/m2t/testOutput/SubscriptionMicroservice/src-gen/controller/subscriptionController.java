package uk.ac.york.eng2.videos.controllers;

import io.micronaut.http.*;
import io.micronaut.http.annotation.*;
import uk.ac.york.eng2.videos.domain.*;
import uk.ac.york.eng2.videos.dto.*;
import java.util.*;

abstract class SubscriptionControllerAbstract {
	
	@Get("/")
	abstract public Set(Videos) listAll();
		
		
	
}
