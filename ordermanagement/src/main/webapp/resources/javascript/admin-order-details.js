document.addEventListener('DOMContentLoaded', () => {
	
	const total = Array.from(document.getElementsByClassName('total'))
	const allTotalTd = document.getElementById('allTotalTd')
	
	if(total && allTotalTd) {
		let allTotal = 0
		for(let i = 0; i < total.length; i++) {
			allTotal += parseInt(total[i].innerText)
		}
		allTotalTd.innerText = allTotal
	}
})