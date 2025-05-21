document.addEventListener("DOMContentLoaded", () => {
	
	const profilePhotoForm = document.getElementById("profilePhotoForm");
	const profilePhotoInput = document.getElementById("profilePhotoInput");
	const profilePhotoButton = document.getElementById("profilePhotoButton");
	
	if(profilePhotoForm && profilePhotoInput && profilePhotoButton) {
		profilePhotoButton.addEventListener("click", () => {
			profilePhotoInput.click();
		});
		
		profilePhotoInput.addEventListener("change", () => {
			profilePhotoForm.submit();
		});
	}
	
});