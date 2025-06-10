<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="id" required="true" %>
<%@ attribute name="categoryId" required="true" %>
<%@ attribute name="categoryName" required="true" %>
<%@ attribute name="englishName" required="true" %>
<%@ attribute name="burmeseName" required="true" %>
<%@ attribute name="unitPrice" required="true" %>
<%@ attribute name="description" required="true" %>
<%@ attribute name="imagePath" required="true" %>
<%@ attribute name="ingredients" required="true" %>

<div class="card fixed-card-size"
	data-id="${id}"
	data-category-id="${categoryId}"
	data-category-name="${categoryName}"
	data-english-name="${englishName}"
	data-burmese-name="${burmeseName}"
	data-unit-Price="${unitPrice}"
	data-description="${description}"
	data-image-path="${imagePath}"
	data-ingredients="${ingredients}"
	>
	<div class="card-header">
		<img data-image="${imagePath}" src="${imagePath}" alt="${imagePath}" class="card-img fixed-img-card-size" />
	</div>
	<div class="card-body">
		<h5 data-english-name="${englishName}" class="card-title">
			${englishName} - ${unitPrice}
		</h5>
		<p data-burmese-name="${burmeseName}" class="card-text">
			(${burmeseName})
		</p>
		<p data-description="${description}" class="card-text">
			${description}
		</p>
	</div>
</div>
