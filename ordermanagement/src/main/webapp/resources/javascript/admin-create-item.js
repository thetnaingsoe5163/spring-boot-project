document.addEventListener('DOMContentLoaded', () => {

	var itemIndex = 0

	const ingredientCountInput = document.getElementById('ingredientCount')
	const listGroup = document.getElementById('listGroup')
	const addBtn = document.getElementById('addBtn')
	const form = document.getElementById('createItemForm')
	const formBtn = document.getElementById('formBtn')

	const imageBtn = document.getElementById('imageBtn')
	const imageInput = document.getElementById('imageInput')
	const preview = document.getElementById('preview')

	if (imageBtn && imageInput && preview) {

		const previewImage = (file) => {
			const reader = new FileReader()
			reader.onload = (e) => {
				preview.src = e.target.result
				preview.classList.remove("d-none")
			}
			reader.readAsDataURL(file)
		}

		imageBtn.addEventListener('click', (e) => {
			e.preventDefault()
			imageInput.click()
		})

		imageInput.addEventListener('change', () => {
			const file = imageInput.files[0]

			if (file) {
				previewImage(file)			
			}
		})
	}

	if (ingredientCountInput && listGroup && addBtn && form && formBtn) {

		const createFinalIngredients = () => {
			const lis = listGroup.getElementsByTagName('li');
			const lisArr = Array.from(lis)

			let index = 0
			for (let i = 0; i < lisArr.length; i++) {
				const li = lisArr[i]
				const input = li.querySelector('input')

				if (input.dataset['deleted'] !== 'true' && input.value !== '') {
					input.setAttribute('name', `ingredients[${index}]`)
					index++
				} else {
					listGroup.removeChild(li)
				}
			}
		}

		form.addEventListener('submit', (e) => {
			e.preventDefault()
		})


		formBtn.addEventListener('click', () => {

			createFinalIngredients()
			form.submit()
		})

		const updateItemIndex = () => {
			var liItem = listGroup.children
			itemIndex = 0

			for (let i = 0; i < liItem.length; i++) {
				const li = liItem[i]
				const input = li.querySelector('input')
				const btn = li.querySelector('button')

				if (input && btn) {
					li.setAttribute('id', `${itemIndex}`)
					input.setAttribute('name', `ingredients[${itemIndex}]`)
					btn.setAttribute('id', `map${itemIndex}`)
					itemIndex++
				}
			}

		}


		const createListGroupItemDiv = () => {

			var li = document.createElement('li')
			li.classList.add('list-group-item')
			li.setAttribute('id', `${itemIndex}`)


			li.innerHTML = `
			<div class="input-group">
				<input data-deleted="false" class="form-control" name="ingredients[${itemIndex}]" />
				<button id="map${itemIndex}" class="btn deleteBtn">
					<i class="bi bi-trash text-danger"></i>
				</button>
			</div>
			`

			listGroup.appendChild(li)

			Array.from(document.getElementsByClassName('deleteBtn')).forEach(item => {
				item.addEventListener('click', (e) => {
					e.preventDefault()

					const index = item.getAttribute('id').substring(3)
					const li = document.getElementById(index)
					li.classList.add('d-none')

					li.querySelector('input').dataset['deleted'] = 'true'

					updateItemIndex()
				})
			})

			updateItemIndex()
			ingredientCountInput.value = listGroup.children.length
		}

		const removeChildFromListGroup = () => {
			while (listGroup.childElementCount) {
				let child = listGroup.firstElementChild;
				listGroup.removeChild(child)
			}
			updateItemIndex()
		}

		ingredientCountInput.addEventListener('change', (e) => {

			const value = ingredientCountInput.value;

			if (listGroup.childElementCount) {
				removeChildFromListGroup()

			}

			for (let i = 0; i < value; i++) {
				createListGroupItemDiv()
			}
		})

		addBtn.addEventListener('click', () => {
			createListGroupItemDiv()
		})
	}
})