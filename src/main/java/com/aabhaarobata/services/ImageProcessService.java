package com.aabhaarobata.services;

import java.io.IOException;

import com.aabhaarobata.web.forms.ImageProcessForm;

public interface ImageProcessService {
	void process(long imageid, ImageProcessForm imageProcessForm) throws IOException;
}
