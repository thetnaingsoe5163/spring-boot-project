var contextPath = document.getElementById('context-path').innerText
var socket = new SockJS(contextPath + '/ws')
var stompClient = Stomp.over(socket)

stompClient.connect({}, function(frame) {
	console.log('Connected')

	stompClient.subscribe('/topic/orders', function(message) {
		var order = JSON.parse(message.body)
		
		if(order.status === 'INPROGRESS') {
			console.log('inprogress')
			addInProgressTable(order);
		}
	})
})

function addInProgressTable(order) {
	
	const table = document.getElementById('inprogress')
	
	const tr = document.createElement('tr')
	
	tr.innerHTML = `
	<td class="bg-success-subtle">${order.tableNumber}</td>
	<td class="bg-success-subtle">${order.id}</td>
	<td class="bg-success-subtle">${order.status}</td>
	<td class="bg-success-subtle">
		<div class="d-flex gap-3 justify-content-end" role="group">
			<a href="${contextPath}/admin/order/pay/${order.id}" class="btn btn-outline-dark">
				Pay Bill
			</a>		
			<a href="${contextPath}/admin/order/details/${order.id}" class="btn btn-outline-info">
				<i class="bi bi-info-circle-fill"></i> Details
			</a>
			<a href="${contextPath}/admin/order/immediate-approve/${order.id}" class="btn btn-outline-success">
				<i class="bi bi-check2"></i> Approve
			</a>		
			<a href="${contextPath}/admin/order/check/${order.id}" class="btn btn-link">
				<i class="bi bi-info-circle"></i>
			</a>														
		</div>
	</td>
	`
	const existTr = document.getElementById(`${order.id}`)
	if(existTr) {
		existTr.querySelectorAll('td').forEach(i => i.classList.add('bg-success-subtle'))
	} else {
		table.querySelector('tbody').insertBefore(tr, table.querySelector('tbody').firstChild)	
	}
}
