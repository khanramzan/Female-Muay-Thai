package rams.app.domain.service;

import org.springframework.stereotype.Service;

import rams.app.domain.model.MediaTable;



public interface MediaTableService {
	
	public MediaTable getRandomClassicMedia();
	
	public MediaTable save(MediaTable mediaTable);
	
}
