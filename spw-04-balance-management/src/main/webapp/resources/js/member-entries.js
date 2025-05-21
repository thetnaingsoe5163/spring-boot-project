document.addEventListener('DOMContentLoaded', () => {
	
	const editForm = document.getElementById('editForm')
	const addItemBtn = document.getElementById('addItemButton')
	if(editForm && addItemBtn) {
		console.log('addItem')
		addItemBtn.addEventListener('click', () => {
			editForm.action = addItemBtn.dataset['addUrl']
			editForm.submit()
		})
	}
	
	const deleteBtn = document.getElementsByClassName('deleteButton')
	if(deleteBtn) {
		Array.from(deleteBtn).forEach(btn => {
			btn.addEventListener('click', () => {
				const itemIndex = btn.dataset['index']
				const item = document.getElementById(`items${itemIndex}.deleted`)
				item.value = true
				
				editForm.action = btn.dataset['deletedUrl']
				editForm.submit()
			})
		})
	}
	
	const setTotalData = () => {
		const itemContainer = document.getElementById('entryItemContainer')
		const childItems = Array.from(itemContainer.children)
		const totalAmountInput = document.getElementById('totalAmount')
		
		let totalAmount = 0
		for(let i = 0; i < childItems.length; i++) {
			console.log(`row${i}`)
			const item = document.getElementById(`row${i}`)
			
			let unitPrice = document.getElementById(`items${i}.unitPrice`).value
			let quantity = document.getElementById(`items${i}.quantity`).value
			let total = unitPrice * quantity
			totalAmount += total
			
			item.innerText = total
		}
		totalAmountInput.value = totalAmount
	} 
	
	const changesUnitPrice = Array.from(document.getElementsByClassName('changesListenerForUnitPrice'))
	changesUnitPrice.forEach(i => {
		i.addEventListener('change', () => {
			setTotalData()
		})
	})
	
	const changesQuantity = Array.from(document.getElementsByClassName('changesListenerForQuantity'))
	changesQuantity.forEach(i => {
		i.addEventListener('change', () =>{
			setTotalData()
		})
	})
	
	setTotalData()
})