document.addEventListener('DOMContentLoaded', () => {

	const menuItems = Array.from(document.getElementsByClassName('card'))

	if (menuItems) {
		
		const removeLi = (root) => {
			while (root.childElementCount) {
				let child = root.firstElementChild;
				root.removeChild(child)
			}
		}

		const createLi = (arr, root) => {
			
			removeLi(root)	

			for (let i = 0; i < arr.length; i++) {
				const li = document.createElement('li')
				li.classList.add('list-group-item')
				li.innerText = arr[i]
				root.appendChild(li)
			}

		}

		menuItems.forEach(item => {
			item.addEventListener('click', (e) => {
				const card = e.currentTarget

				const engName = document.getElementById('modal-english-name')
				const burName = document.getElementById('modal-burmese-name')
				const description = document.getElementById('modal-description')
				const ingredients = document.getElementById('modal-ingredients')
				const img = document.getElementById('modal-img')
				const unitPrice = document.getElementById('modal-unit-price')
				
				const id = document.getElementById('id')
				const categoryId = document.getElementById('categoryId')
				const hiddenEngName = document.getElementById('hiddenEnglishName')
				const hiddenBurName = document.getElementById('hiddenBurmeseName')
				const hiddenUnitPrice = document.getElementById('hiddenUnitPrice')

				if (engName && burName && description 
						&& ingredients && img && id && unitPrice 
						&& categoryId && hiddenEngName 
						&& hiddenBurName && hiddenUnitPrice) {

					engName.innerText = card.dataset['englishName']
					burName.innerText = card.dataset['burmeseName']	
					description.innerText = card.dataset['description']
					unitPrice.innerText = card.dataset['unitPrice']
					img.setAttribute('src', card.dataset['imagePath'])
					img.setAttribute('alt', card.dataset['imagePath'])
					
					id.value = card.dataset['id']
					categoryId.value = card.dataset['categoryId']
					hiddenUnitPrice.value = card.dataset['unitPrice']
					
					hiddenEngName.value = engName.innerText
					hiddenBurName.value = burName.innerText

					const arr = card.dataset['ingredients']
						.slice(1, -1)
						.split(',')
						.map(item => item.trim())			

					createLi(arr, ingredients)
				}

				const modal = new bootstrap.Modal('#orderModal')
				modal.show()
			})
		})
	}
	
	/**
	const categoryLinks = Array.from(document.getElementsByClassName('category-link'))
	categoryLinks.forEach(link => {
		link.addEventListener('click', (e) => {

			const uri = link.dataset['uri']
			console.log(uri)
			
			fetch(uri)
			.then(resp => {
				if(!resp.ok) {
					throw new Error('Error occurs while fetching data.')
				}
				return resp.json()
			})
			.then(data => {
				console.log(data)
			})
			.catch(error => {
				console.log(error)
			})
		})
	})
	*/

})
