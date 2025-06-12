document.addEventListener('DOMContentLoaded', () => {

	const originalValue = {}

	var trList = document.querySelectorAll('tbody > tr')

	for (let i = 0; i < trList.length; i++) {
		const price = document.getElementById(`price${i}`).value
		const quantity = document.getElementById(`quantity${i}`).value

		originalValue[`price${i}`] = price
		originalValue[`quantity${i}`] = quantity
	}

	const setTotalAmount = (index) => {
		const price = document.getElementById(`price${index}`).value
		const quantity = document.getElementById(`quantity${index}`).value
		document.getElementById(`total${index}`).innerText = price * quantity
	}

	const setAllTotalAmount = () => {
		const total = Array.from(document.getElementsByClassName('total'))
		const allTotalTd = document.getElementById('allTotalTd')

		if (total && allTotalTd) {
			let allTotal = 0
			for (let i = 0; i < total.length; i++) {
				allTotal += parseInt(total[i].innerText)
			}
			allTotalTd.innerText = allTotal
		}
	}

	setAllTotalAmount()

	Array.from(document.getElementsByClassName('price')).forEach(i => {
		i.addEventListener('change', (e) => {

			const key = e.currentTarget.getAttribute('id')
			let value = e.currentTarget.value
			const index = key.charAt(key.length - 1)

			let quantity = document.getElementById(`quantity${index}`).value

			if (quantity < 1) {
				quantity = 1
				document.getElementById(`quantity${index}`).value = quantity
			}

			if (value < 0) {
				value = 0
				e.currentTarget.value = value
			}

			const reason = document.getElementById(`reason${index}`)

			if (value !== originalValue[key] || quantity !== originalValue[`quantity${index}`]) {
				reason.disabled = false
				reason.required = true
				document.getElementById(`modified${index}`).value = 'true'

				setTotalAmount(index)
			}

			if (value === originalValue[key] && quantity === originalValue[`quantity${index}`]) {
				reason.disabled = true
				reason.required = false
				document.getElementById(`modified${index}`).value = 'false'

				setTotalAmount(index)
			}

			setAllTotalAmount()
		})
	})

	Array.from(document.getElementsByClassName('quantity')).forEach(i => {
		i.addEventListener('change', (e) => {
			const key = e.currentTarget.getAttribute('id')
			let value = e.currentTarget.value
			const index = key.charAt(key.length - 1)

			let price = document.getElementById(`price${index}`).value

			if (price < 0) {
				price = 0
				document.getElementById(`price${index}`).value = value
			}

			if (value > 0) {
				document.getElementById(`deleted${index}`).value = 'false'

				Array.from(document.getElementsByClassName(`td${index}`))
					.forEach(td => {
						td.classList.remove('b-and-w-theme-bg-1')
					})
			}

			if (value <= 0) {
				value = 0
				e.currentTarget.value = value
				document.getElementById(`deleted${index}`).value = 'true'

				Array.from(document.getElementsByClassName(`td${index}`))
					.forEach(td => {
						td.classList.add('b-and-w-theme-bg-1')
					})
			}

			const reason = document.getElementById(`reason${index}`)

			if (value !== originalValue[key] || price !== originalValue[`price${index}`]) {
				reason.disabled = false
				reason.required = true
				document.getElementById(`modified${index}`).value = 'true'

				setTotalAmount(index)
			}

			if (value === originalValue[key] && price === originalValue[`price${index}`]) {
				reason.disabled = true
				reason.required = false
				document.getElementById(`modified${index}`).value = 'false'

				setTotalAmount(index)
			}
			setAllTotalAmount()
		})
	})

	const resetBtn = document.getElementById('reset')
	if (resetBtn) {
		resetBtn.addEventListener('click', (e) => {
			e.preventDefault()
			for (let key in originalValue) {
				document.getElementById(key).value = originalValue[key]
				let index = key.charAt(key.length - 1)
				document.getElementById(`reason${index}`).disabled = true
				document.getElementById(`reason${index}`).required = false
				document.getElementById(`modified${index}`).value = 'false'
				document.getElementById(`deleted${index}`).value = 'false'

				Array.from(document.getElementsByClassName(`td${index}`))
					.forEach(td => {
						td.classList.remove('b-and-w-theme-bg-1')
					})
				setTotalAmount(index)
			}
			setAllTotalAmount()
		})

		document.querySelectorAll('textarea').forEach(i => i.disabled = true)

	}
})