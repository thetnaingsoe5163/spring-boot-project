document.addEventListener("DOMContentLoaded", () => {
	
	const searchFrom = document.getElementById("searchForm")
	const pageInput = document.getElementById("pageInput")
	const sizeInput = document.getElementById("sizeInput")
	const sizeSelect = document.getElementById("sizeSelect")
	
	if(searchFrom && pageInput && sizeInput && sizeSelect) {
		
		sizeSelect.addEventListener("change", () => {
			pageInput.value = '0'
			sizeInput.value = sizeSelect.value
				
			searchFrom.submit()
		})
		
		Array.from(document.getElementsByClassName('pageLink')).forEach(link => {

			link.addEventListener('click', (event) => {
				event.preventDefault()		
				pageInput.value = link.dataset['pageNumber']
				
				searchFrom.submit()
			})
		})
	}
})