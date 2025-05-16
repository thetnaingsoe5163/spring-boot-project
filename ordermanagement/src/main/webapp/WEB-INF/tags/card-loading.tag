<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="cssClass"%>
<div class="card ${cssClass}">
	<div class="card-header placeholder" style="width: 100%; height: 200px">
		<img src="..." alt="..." class="card-img-top" />
	</div>
	<div class="card-body">
		<h5 class="card-title placeholder-glow">
			<span class="placeholder col-6">
			</span>
		</h5>
		<p class="card-text placeholder-glow">
			<span class="placeholder col-7"></span>
			<span class="placeholder col-6"></span>
			<span class="placeholder col-5"></span>
			<span class="placeholder col-7"></span>
			<span class="placeholder col-4"></span>
		</p>
		<a class="btn btn-primary disabled placeholder col-6" aria-disabled="true"></a>
	</div>
</div>