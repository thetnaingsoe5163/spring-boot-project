<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="imagePath" required="true" %>
<%@ attribute name="englishName" required="true" %>
<%@ attribute name="burmeseName" required="true" %>
<%@ attribute name="description" required="true" %>

<div class="card fixed-card-size">
	<div class="card-header">
		<img data-image="${imagePath}" src="${imagePath}" alt="${imagePath}" class="card-img fixed-img-card-size" />
	</div>
	<div class="card-body">
		<h5 data-english-name="${englishName}" class="card-title">
			${englishName}
		</h5>
		<p data-burmese-name="${burmeseName}" class="card-text">
			(${burmeseName})
		</p>
		<p data-description="${description}" class="card-text">
			${description}
		</p>
	</div>
</div>
