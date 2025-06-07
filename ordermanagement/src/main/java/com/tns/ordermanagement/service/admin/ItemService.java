package com.tns.ordermanagement.service.admin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tns.ordermanagement.controller.admin.dto.AddItemForm;
import com.tns.ordermanagement.controller.commondto.ItemDto;
import com.tns.ordermanagement.exception.AppBusinessException;
import com.tns.ordermanagement.model.entity.Item;
import com.tns.ordermanagement.model.repo.admin.CategoryRepo;
import com.tns.ordermanagement.model.repo.admin.ItemRepo;
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

		if (form.getImageFile() != null) {
			var fileName = getValidFileName(form.getImageFile(), form.getEnglishName());
			var filePath = request.getServletContext().getRealPath("/resources/images/items");

			savePhoto(form.getImageFile(), fileName, filePath);
			item.setImage(fileName);
		}

		itemRepo.save(item);
	}
	
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

	private String getValidFileName(MultipartFile file, String itemName) {

		var item = itemRepo.findByEnglishName(itemName);
		var fileName = file.getOriginalFilename();

		var arr = fileName.split("\\.");
		var extension = arr[arr.length - 1];

		if (item.isPresent()) {
			throw new AppBusinessException("Item already exists.");
		}

		return "%s.%s".formatted(itemName, extension);
	}
}
