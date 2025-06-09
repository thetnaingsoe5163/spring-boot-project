document.addEventListener('DOMContentLoaded', () => {

	const table = document.getElementById('table-body')
	const trs = table.querySelectorAll('tr')
	const form = document.getElementById('orderDetailsForm')

	if (trs && form) {
		for (let i = 0; i < trs.length; i++) {

			const deleted = document.getElementById(`deleted${i}`)
			const hiddenQuantity = document.getElementById(`hiddenQuantity${i}`)

			const spanQuantity = document.getElementById(`spanQuantity${i}`)
			const minusBtn = document.getElementById(`minusBtn${i}`)
			const addBtn = document.getElementById(`addBtn${i}`)
			const tr = document.getElementById(`${i}`)

			if (deleted && hiddenQuantity && spanQuantity && minusBtn && addBtn) {

				if (parseInt(spanQuantity) >= 1) {
					deleted.value = 'false'
				}

				minusBtn.addEventListener('click', (e) => {
					e.preventDefault()

					let q = parseInt(spanQuantity.innerText)
					q--
					hiddenQuantity.value = q
					spanQuantity.innerText = q

					if (q < 1) {
						deleted.value = 'true'
						hiddenQuantity.value = q
						spanQuantity.value = q
						tr.classList.add('d-none')
						
						form.action = form.dataset['remove']
						form.submit()
					}
				})

				addBtn.addEventListener('click', (e) => {
					e.preventDefault()

					let q = parseInt(spanQuantity.innerText)
					q++
					hiddenQuantity.value = q
					spanQuantity.innerText = q
				})
			}

		}
	}
})