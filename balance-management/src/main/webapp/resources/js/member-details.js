document.addEventListener("DOMContentLoaded", () => {
	
	const changeStatusBtn = document.getElementById('changeStatusButton')
	const changeStatusDialog = new bootstrap.Modal('#changeStatusDialog')
	
	if(changeStatusBtn && changeStatusDialog) {
		changeStatusBtn.addEventListener('click', () => {
			changeStatusDialog.show()
		})
	}
})