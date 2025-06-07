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

				if (engName && burName && description && ingredients) {

					engName.innerText = card.dataset['englishName']
					burName.innerText = card.dataset['burmeseName']
					description.innerText = card.dataset['description']

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

	const categoryLinks = Array.from(document.getElementsByClassName('category-link'))
	categoryLinks.forEach(link => {
		link.addEventListener('click', (e) => {
			e.preventDefault()

			const uri = link.dataset['uri']
			console.log(uri)
		})
	})

})
