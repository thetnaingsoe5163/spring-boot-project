package com.jdc.online.balances.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Patch_20250510 {
	
	@Value("${app.patch.p20250510.run}")
	private boolean patchRun;

//	if there is any error to fix
//	@EventListener(classes = ContextRefreshedEvent.class)
//	public void execute() {
//		
//		if(patchRun) {
//			
//		}
//	}
}
