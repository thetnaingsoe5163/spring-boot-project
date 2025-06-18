package com.tns.ordermanagement.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tns.ordermanagement.controller.admin.dto.AddItemForm;
import com.tns.ordermanagement.controller.commondto.ItemDto;
import com.tns.ordermanagement.model.entity.Item;
import com.tns.ordermanagement.model.repo.CategoryRepo;
import com.tns.ordermanagement.model.repo.ItemRepo;
import com.tns.ordermanagement.utils.utilityclass.SafeClass;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

	private final ItemRepo itemRepo;
	private final CategoryRepo categoryRepo;

	public void insert(AddItemForm form, HttpServletRequest request) {
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
}
