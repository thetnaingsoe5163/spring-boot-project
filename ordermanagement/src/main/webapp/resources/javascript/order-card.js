document.addEventListener('DOMContentLoaded', () => {
	
	const addBtn = document.getElementById('addBtn')
	const minusBtn = document.getElementById('minusBtn')
	const spanQuantity = document.getElementById('spanQuantity')
	const hiddenQuantity = document.getElementById('hiddenQuantity')
	
	if(addBtn && minusBtn && spanQuantity && hiddenQuantity) {
		addBtn.addEventListener('click', (e) => {
			e.preventDefault()
			spanQuantity.innerText = parseInt(spanQuantity.innerText) + 1
			hiddenQuantity.value = spanQuantity.innerText
		})
		
		minusBtn.addEventListener('click', (e) => {
			e.preventDefault()
			let q = parseInt(spanQuantity.innerText) - 1
			if(q < 0) {
				q = 0 
			}
			spanQuantity.innerText = q
			hiddenQuantity.value = q
		})
	}
})