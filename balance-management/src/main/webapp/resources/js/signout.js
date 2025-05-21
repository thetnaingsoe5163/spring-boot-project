document.addEventListener("DOMContentLoaded", () => {
	
	const signOutMenu = document.getElementById('signOutMenu')
	const signOutForm = document.getElementById('signOutForm')
	
	if(signOutMenu && signOutForm) {
		signOutMenu.addEventListener('click', (event) => {
			event.preventDefault()
			signOutForm.submit()
		})
	}
})