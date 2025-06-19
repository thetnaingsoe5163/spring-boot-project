package com.tns.ordermanagement.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tns.ordermanagement.controller.admin.dto.ItemEditingForm;
import com.tns.ordermanagement.controller.commondto.ItemDto;
import com.tns.ordermanagement.model.entity.Item;
import com.tns.ordermanagement.model.repo.CategoryRepo;
import com.tns.ordermanagement.model.repo.ItemRepo;
import com.tns.ordermanagement.utils.utilityclass.SafeClass;

import static com.tns.ordermanagement.utils.utilityclass.SafeClass.safeCall;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

	private final ItemRepo itemRepo;
	private final CategoryRepo categoryRepo;

	public void insert(ItemEditingForm form, HttpServletRequest request) {
		var category = SafeClass.safeCall(categoryRepo.findById(form.getCategory()), "Category", form.getCategory());

		var item = new Item();
		item.setCategory(category);
		item.setEnglishName(form.getEnglishName());
		item.setBurmeseName(form.getBurmeseName());
		item.setDescription(form.getDescription());
		item.setIngredients(form.getIngredients());
		item.setUnitPrice(form.getUnitPrice());
		
		item = itemRepo.save(item);
		
		if (form.getImageFile() != null && !form.getImageFile().isEmpty()) {
			var fileName = getValidFileName(form.getImageFile(), item.getId());
			var filePath = request.getServletContext().getRealPath("/resources/images/items");

			savePhoto(form.getImageFile(), fileName, filePath);
			item.setImage(fileName);
			itemRepo.save(item);
		}

	}
	
	@Transactional(readOnly = true)
	public List<ItemDto> findAll() {
		return itemRepo.findAll().stream().map(ItemDto::new).toList();
	}
	
	private void savePhoto(MultipartFile file, String name, String folder) {

		var path = Path.of(folder, name);

		try {
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new IllegalArgumentException("File operation goes wrong.");
		}

	}

	private String getValidFileName(MultipartFile file, Integer id) {

		var fileName = file.getOriginalFilename();

		var arr = fileName.split("\\.");
		var extension = arr[arr.length - 1];

		return "%d.%s".formatted(id, extension);
	}

	@Transactional(readOnly = true)
	public List<ItemDto> findByCategoryId(int id) {
		return itemRepo.findByCategory_Id(id).stream().map(ItemDto::new).toList();
	}
	
	public ItemEditingForm getEditFormById(int id, ItemEditingForm form) {
		var item = safeCall(itemRepo.findById(id), "Item ID", id);
		
		form.setItemId(item.getId());
		form.setCategory(item.getCategory().getId());
		form.setEnglishName(item.getEnglishName());
		form.setBurmeseName(item.getBurmeseName());
		form.setDescription(item.getDescription());
		form.setUnitPrice(item.getUnitPrice());
		form.setIngredients(item.getIngredients());
		form.setImageName(item.getImage() == null || item.getImage().isBlank() ? null : item.getImage());
		
		return form;
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void update(ItemEditingForm form, HttpServletRequest request) {
		
		var item = safeCall(itemRepo.findById(form.getItemId()), "Item ID", form.getItemId());
		var category = safeCall(categoryRepo.findById(form.getCategory()), "Category ID", form.getCategory());
		
		item.setCategory(category);
		item.setEnglishName(form.getEnglishName());
		item.setBurmeseName(form.getBurmeseName());
		item.setDescription(form.getDescription());
		item.setUnitPrice(form.getUnitPrice());
		item.setIngredients(form.getIngredients());
		
		if(!form.getImageFile().isEmpty()) {
			var fileName = getValidFileName(form.getImageFile(), item.getId());
			var filePath = request.getServletContext().getRealPath("/resources/images/items");
			
			savePhoto(form.getImageFile(), fileName, filePath);
			item.setImage(fileName);
		}
		
	}

	@Transactional(readOnly = true)
	public Object findByItemId(int id) {
		var item = safeCall(itemRepo.findById(id), "Item ID", id);
		return new ItemDto(item);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void deleteItemById(int id, HttpServletRequest request) {	
		var item = safeCall(itemRepo.findById(id), "Item ID", id);
		
		if(item.getImage() != null) {
			var file = request.getServletContext().getRealPath("/resources/images/items/").concat(item.getImage());
			removePhoto(file);
		}
		
		itemRepo.delete(item);
	}
	
	private void removePhoto(String filePath) {
		var file = new File(filePath);
		if (file.exists() && file.isFile()) {
			file.delete();
		}
	}
}
