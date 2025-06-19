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
	<div class="card-header placeholder-glow" id="parent-placeholder${id}">
		<div class="card-img fixed-img-card-size placeholder" id="child-placeholder${id}">
			
		</div>
		<!-- Testing placeholder 
		<img onload="loadActualImage(this, 'parent-placeholder${id}', 'child-placeholder${id}')" 
			src="https://www.shutterstock.com/shutterstock/photos/2631844399/display_1500/stock-photo-bangkok-thailan-may-long-delays-on-asok-montri-rd-are-common-due-to-traffic-far-2631844399.jpg" alt="${imagePath}" class="card-img fixed-img-card-size d-none" />		  
		 -->
		<img data-image="${imagePath}" onload="loadActualImage(this, 'parent-placeholder${id}', 'child-placeholder${id}')" 
			src="${imagePath}" alt="${imagePath}" class="card-img fixed-img-card-size d-none" />
	</div>
	<div class="card-body">
		<h5 data-english-name="${englishName}" class="card-title">
			${englishName} - ${unitPrice}
		</h5>
		<p data-burmese-name="${burmeseName}" class="card-text">
			(${burmeseName})
		</p>
		<p data-description="${description}" class="card-text adjust-overflow">
			${description}
		</p>
	</div>
</div>
