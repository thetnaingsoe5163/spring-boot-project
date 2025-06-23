document.addEventListener('DOMContentLoaded', () => {
	
	const password = document.getElementById('password')
	const confirmPassword = document.getElementById('confirmPassword')
	const btn = document.getElementById('btn')
	const matchMessage = document.getElementById('matchMessage')
	
	if(password && confirmPassword && btn && matchMessage) {
		confirmPassword.addEventListener('change', () => {
			if(password.value === confirmPassword.value) {
				btn.disabled = false
				matchMessage.classList.add('d-none')
			} else {
				btn.disabled = true
				matchMessage.classList.remove('d-none')
			}
		})
	}
	
})