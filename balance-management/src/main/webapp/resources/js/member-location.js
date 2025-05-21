document.addEventListener('DOMContentLoaded', () => {

	const regionSelect = document.getElementById('region')
	const districtSelect = document.getElementById('district')
	const townshipSelect = document.getElementById('township')

	if (regionSelect && districtSelect && townshipSelect) {

		const optionForNoElement = (name, select) => {
			const option = document.createElement('option')
			option.innerText = `Select ${name}`
			removeChild(select)
			select.appendChild(option)
		}

		const removeChild = (select) => {
			Array.from(select.children).forEach(option => {
				select.removeChild(option)
			})
		}

		const setDistrict = (result) => {
			if (result) {

				removeChild(districtSelect)
				const arr = Array.from(result)
				
				const selectDistrictOption = document.createElement('option')
				selectDistrictOption.innerText = 'Select District'
				districtSelect.appendChild(selectDistrictOption)

				arr.forEach(item => {
					const option = document.createElement('option')
					option.value = item.id
					option.innerText = item.name
					firstItem = false
					districtSelect.appendChild(option)
				})
			} else {
				optionForNoElement('District', districtSelect)
			}
		}

		const setTownship = (result) => {
			if (result) {
				
				removeChild(townshipSelect)
				const arr = Array.from(result)
				
				const selectTownshipOption = document.createElement('option')
				selectTownshipOption.innerText = 'Select Township'
				townshipSelect.appendChild(selectTownshipOption)

				arr.forEach(item => {
					const option = document.createElement('option')
					option.value = item.id
					option.innerText = item.name
					townshipSelect.appendChild(option)
				})
			} else {
				optionForNoElement('Township', townshipSelect)
			}
		}

		regionSelect.addEventListener('change', () => {

			const regionId = regionSelect.value
			if (regionId) {
				
				fetchApi = `${regionSelect.dataset['fetchApi']}/${regionId}`
				fetch(fetchApi)
					.then(async response => {
						const result = await response.json()
						setDistrict(result)
					})
			} else {
				optionForNoElement('District', districtSelect)
			}
		})

		districtSelect.addEventListener('change', () => {

			const districtId = districtSelect.value
			if (districtId) {
				
				fetchApi = `${districtSelect.dataset['fetchApi']}/${districtId}`
				fetch(fetchApi)
					.then(async response => {
						const result = await response.json()
						setTownship(result)
					})
			} else {
				optionForNoElement('Township', townshipSelect)
			}
		})
	}
})