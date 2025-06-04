document.addEventListener('DOMContentLoaded', () => {
	
	const addCategoryModal = new bootstrap.Modal('#addCategoryModal')
	const addCategoryLink = document.getElementById('addNewCategoryLink')
	const addCategoryForm = document.getElementById('addCategoryForm')
	const modalBtn = document.getElementById('modalBtn')
	
	if(addCategoryModal && addCategoryLink && addCategoryForm && modalBtn) {
		addCategoryLink.addEventListener('click', (e) => {
			e.preventDefault()
			addCategoryModal.show()
		})
		
		modalBtn.addEventListener('click', (e) => {
			e.preventDefault()
			addCategoryForm.submit()
		})
	}
	
	const openModalFlag = document.getElementById('openModalFlag')
	if(openModalFlag) {
		addCategoryModal.show()
		
	}
})