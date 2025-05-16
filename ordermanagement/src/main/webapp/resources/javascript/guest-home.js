document.addEventListener('DOMContentLoaded', () => {
	
	const menuItems = Array.from(document.getElementsByClassName('card'))
	
	if(menuItems) {
		menuItems.forEach(item => {	
			item.addEventListener('click', () => {
				const modal = new bootstrap.Modal('#orderModal')
				modal.show()
			})
		})
	}
	
})
