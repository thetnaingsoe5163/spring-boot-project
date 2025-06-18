document.addEventListener('DOMContentLoaded', () => {
	
	const historyUrl = document.getElementById('historyUrl')

	if (historyUrl) {

		historyUrl.addEventListener('click', () => {
			let url = historyUrl.getAttribute('href')

			let customerSessionId = localStorage.getItem("customerSessionId")

			if (!customerSessionId) {
				historyUrl.classList.add('d-none')
			} else {
				historyUrl.setAttribute('href', url.concat(`?id=${customerSessionId}`))
				
			}

		})

	}
})