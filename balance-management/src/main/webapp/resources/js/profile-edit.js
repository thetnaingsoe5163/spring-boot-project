document.addEventListener("DOMContentLoaded", () => {
	
	const profilePhotoForm = document.getElementById("profilePhotoForm");
	const profilePhotoInput = document.getElementById("profilePhotoInput");
	const profilePhotoButton = document.getElementById("profilePhotoButton");
	
	const profileEditForm = document.getElementById("profileEditForm")
	const profileEditBtn = document.getElementById("profileEditBtn")
	
	if(profileEditForm && profileEditBtn) {
		profileEditForm.addEventListener('submit', (e) => {
			e.preventDefault()
		})
		
		profileEditBtn.addEventListener('click', () => {
			profileEditForm.submit()
		})
	}
	
	if(profilePhotoForm && profilePhotoInput && profilePhotoButton) {
		profilePhotoButton.addEventListener("click", () => {
			profilePhotoInput.click();
		});
		
		profilePhotoInput.addEventListener("change", () => {
			profilePhotoForm.submit();
		});
	}
	
});