var contextPath = document.getElementById('context-path').innerText
var socket = new SockJS(contextPath + '/ws')
var stompClient = Stomp.over(socket)

stompClient.connect({}, function(frame) {
	console.log('Connected')

	stompClient.subscribe('/topic/orders', function(message) {
		var order = JSON.parse(message.body)
		addToTable(order)
	})
})

function addToTable(order) {
	const list = document.getElementById('order-list')

	if (list) {
		const tr = document.createElement('tr')
		tr.innerHTML = `
		<td class="bg-success-subtle">${order.id}</td>
		<td class="bg-success-subtle">${order.status}</td>
		<td class="bg-success-subtle">
			<div class="d-flex gap-3 justify-content-end" role="group">
				<a href="${contextPath}/admin/order/details/${order.id}" class="btn btn-outline-info">
					<i class="bi bi-info-circle-fill"></i> Details
				</a>
				<a href="${contextPath}/admin/order/approve/${order.id}" class="btn btn-outline-success">
					<i class="bi bi-check2"></i> Approve
				</a>								
			</div>
		</td>
		`
		if(list.firstChild) {
			list.insertBefore(tr, list.firstChild)
		} else {
			list.appendChild(tr)	
		}
	}
}