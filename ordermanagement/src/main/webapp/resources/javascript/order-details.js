document.addEventListener('DOMContentLoaded', () => {

	const table = document.getElementById('table-body')
	const trs = table.querySelectorAll('tr')
	const form = document.getElementById('orderDetailsForm')

	const setAllTotalPrice = () => {
		const allTotalPrice = document.getElementById('allTotalPrice')
		const totalPriceOfEach = Array.from(document.getElementsByClassName('totalPrice'))

		if (allTotalPrice && totalPriceOfEach) {
			let total = 0
			for(let i = 0; i < totalPriceOfEach.length; i++) {
				console.log(totalPriceOfEach[i].innerText)
				total += parseInt(totalPriceOfEach[i].innerText)
			}
			allTotalPrice.innerText = total
		}
	}
	
	setAllTotalPrice()

	if (trs && form) {
		for (let i = 0; i < trs.length; i++) {

			const deleted = document.getElementById(`deleted${i}`)
			const hiddenQuantity = document.getElementById(`hiddenQuantity${i}`)

			const spanQuantity = document.getElementById(`spanQuantity${i}`)
			const unitPrice = document.getElementById(`unitPrice${i}`)
			const totalPrice = document.getElementById(`totalPrice${i}`)
			const minusBtn = document.getElementById(`minusBtn${i}`)
			const addBtn = document.getElementById(`addBtn${i}`)
			const tr = document.getElementById(`${i}`)

			if (deleted && hiddenQuantity && spanQuantity && minusBtn && addBtn && unitPrice && totalPrice) {

				if (parseInt(spanQuantity) >= 1) {
					deleted.value = 'false'
				}

				totalPrice.innerText = parseInt(unitPrice.innerText) * parseInt(spanQuantity.innerText)

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

					totalPrice.innerText = parseInt(unitPrice.innerText) * parseInt(spanQuantity.innerText)
					setAllTotalPrice()
				})

				addBtn.addEventListener('click', (e) => {
					e.preventDefault()

					let q = parseInt(spanQuantity.innerText)
					q++
					hiddenQuantity.value = q
					spanQuantity.innerText = q

					totalPrice.innerText = parseInt(unitPrice.innerText) * parseInt(spanQuantity.innerText)
					setAllTotalPrice()
				})
			}

		}
		
		setAllTotalPrice()
	}
})