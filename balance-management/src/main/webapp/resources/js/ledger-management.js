document.addEventListener("DOMContentLoaded", () => {
	
	var addnewEntryBtn = document.getElementById("addNewEntryBtn");
	var editDialog = new bootstrap.Modal("#editDialog");
	
	addnewEntryBtn.addEventListener("click", () => {
		
		document.getElementById('editDialogLabel').innerText = 'Add New Category'
		document.getElementById('id').value = ''
		document.getElementById('name').value = ''
		document.getElementById('type').value = ''
		
		editDialog.show()
	})
	

	const status = document.getElementById('status')
	const statusLabel = document.getElementById('status-label')
	const checkLabel = document.getElementById('check-label')

	if(status && statusLabel && checkLabel) {
		status.addEventListener('change', () => {
				// ledger is deleted
				if(status.checked) {
					statusLabel.innerText = 'Ledger is deleted currently'
					statusLabel.classList.remove('text-success')
					statusLabel.classList.add('text-danger')
					checkLabel.innerText = 'Active'
				} else {
					statusLabel.innerText = 'Ledger is active currently'
					statusLabel.classList.remove('text-danger')
					statusLabel.classList.add('text-success')
					checkLabel.innerText = 'Deleted'
				}
			})	
	}
	
	Array.from(document.getElementsByClassName('edit-link')).forEach(link => {
		link.addEventListener('click', (event) => {
			event.preventDefault()
			editDialog.show()
			
			document.getElementById('editDialogLabel').innerText = 'Edit Dialog'
			
			document.getElementById('id').value = link.dataset['id']
			document.getElementById('name').value = link.dataset['name']
			document.getElementById('type').value = link.dataset['type']
			document.getElementById('status').checked = link.dataset['status'] === 'true'
			
		})
	})
})