document.addEventListener('DOMContentLoaded', () => {
	
	const addCategoryModal = new bootstrap.Modal('#addCategoryModal')
	const addCategoryLink = document.getElementById('addNewCategoryLink')
	const addCategoryForm = document.getElementById('addCategoryForm')
	
	if(addCategoryModal && addCategoryLink && addCategoryForm) {
		addCategoryLink.addEventListener('click', (e) => {
			e.preventDefault()
			addCategoryModal.show()
		})
	}
})