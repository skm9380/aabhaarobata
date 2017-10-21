<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
<title>Aabaa Robata</title>
<meta http-equiv="Content-type" content="text/html;charset=UTF-8" />

<script src="js/jquery.min.js"></script>
<script src="js/jquery.Jcrop.js"></script>
<script type="text/javascript">
	var imageID = 0;
	jQuery(function($) {

		var jcrop_api;

		/* 		$('#target').Jcrop({
		 onChange : showCoords,
		 onSelect : showCoords,
		 onRelease : clearCoords
		 }, function() {
		 jcrop_api = this;
		 }); */
		/*
		 $('#coords').on('change','input',function(e){
		 var x1 = $('#x1').val(),
		 x2 = $('#x2').val(),
		 y1 = $('#y1').val(),
		 y2 = $('#y2').val();
		 jcrop_api.setSelect([x1,y1,x2,y2]);
		 });
		 */

	});

	// Simple event handler, called from onChange and onSelect
	// event handlers, as per the Jcrop invocation above
	function showCoords(c) {

		if (document.getElementById("color1").checked) {
			$('#x1').val(c.x);
			$('#y1').val(c.y);
			$('#x2').val(c.x2);
			$('#y2').val(c.y2);
			$('#w').val(c.w);
			$('#h').val(c.h);
		} else if (document.getElementById("color2").checked) {
			$('#x11').val(c.x);
			$('#y11').val(c.y);
			$('#x21').val(c.x2);
			$('#y21').val(c.y2);
			$('#w1').val(c.w);
			$('#h1').val(c.h);

		}else if (document.getElementById("color3").checked) {
			$('#x12').val(c.x);
			$('#y12').val(c.y);
			$('#x22').val(c.x2);
			$('#y22').val(c.y2);
			$('#w2').val(c.w);
			$('#h2').val(c.h);

		}

	};

	function clearCoords() {
		$('#coords input').val('');
	};

	$(document)
			.ready(
					function() {

						initJcrop();
						function initJcrop() {
							jcrop_api = $.Jcrop('#target');
						}
						;

						$('#color1').change(function() {
							if (this.checked) {
								$('#color2').attr('checked', false);
								$('#color3').attr('checked', false);
							}

						});

						$('#color2').change(function() {
							if (this.checked) {
								$('#color1').attr('checked', false);
								$('#color3').attr('checked', false);
							}

						});

						$('#color3').change(function() {
							if (this.checked) {
								$('#color1').attr('checked', false);
								$('#color2').attr('checked', false);
							}

						});						
						
						$('#x1').prop("readonly", true);
						$('#y1').prop("readonly", true);
						$('#x2').prop("readonly", true);
						$('#y2').prop("readonly", true);
						$('#w').prop("readonly", true);
						$('#h').prop("readonly", true);

						$('#x11').prop("readonly", true);
						$('#y11').prop("readonly", true);
						$('#x21').prop("readonly", true);
						$('#y21').prop("readonly", true);
						$('#w1').prop("readonly", true);
						$('#h1').prop("readonly", true);
						
						$('#x12').prop("readonly", true);
						$('#y12').prop("readonly", true);
						$('#x22').prop("readonly", true);
						$('#y22').prop("readonly", true);
						$('#w2').prop("readonly", true);
						$('#h2').prop("readonly", true);						

						$('#submit')
								.on(
										'click',
										function() {

											if (!document
													.getElementById("color1").checked
													&& !document
															.getElementById("color2").checked && !document
															.getElementById("color3").checked) {
												alert("Please select atleast a color ! ");
												return true;
											}
											$('#coords').submit();
										});

						$('#selectcolor').hide();

						$("#uploadFileSubmit")
								.click(
										function(event) {

											//stop submit the form, we will post it manually.
											event.preventDefault();

											// Get form
											var form = $('#uploadfileform')[0];

											// Create an FormData object
											var data = new FormData(form);

											// If you want to add an extra field for the FormData
											data
													.append("CustomField",
															"This is some extra data, testing");

											// disabled the submit button
											$("#uploadFileSubmit").prop(
													"disabled", true);

											$
													.ajax({
														type : "POST",
														enctype : 'multipart/form-data',
														url : "uploadfile",
														data : data,

														processData : false,
														contentType : false,
														cache : false,
														timeout : 600000,
														success : function(data) {
															if (data.statusMsg == 'OK') {

																imageID = data.fileID;
																var newSrc = 'processedimages/'
																		+ imageID
																		+ '/original';

																$('#target')
																		.attr(
																				'src',
																				newSrc);

																$('#target')
																		.Jcrop(
																				{
																					onChange : showCoords,
																					onSelect : showCoords,
																					onRelease : clearCoords
																				},
																				function() {
																					jcrop_api = this;
																				});

																jcrop_api
																		.setImage(newSrc);

																$(
																		'#selectcolor')
																		.fadeIn();

																$("#result")
																		.text(
																				data.statusMsg);
															} else {
																$("#result")
																		.text(
																				data.statusMsg);
															}
															console
																	.log(
																			"SUCCESS : ",
																			data);
															$(
																	"#uploadFileSubmit")
																	.prop(
																			"disabled",
																			false);

														},
														error : function(e) {

															$("#result")
																	.text(
																			e.responseText);
															console.log(
																	"ERROR : ",
																	e);
															$(
																	"#uploadFileSubmit")
																	.prop(
																			"disabled",
																			false);

														}
													});

										});

						$("#selectcolorsubmit")
								.click(
										function(event) {

											//stop submit the form, we will post it manually.
											event.preventDefault();

											// Get form
											var form = $('#coords')[0];

											// Create an FormData object
											var data = new FormData(form);

											// If you want to add an extra field for the FormData
											data.append("imageid", imageID);

											// disabled the submit button
											$("#selectcolorsubmit").prop(
													"disabled", true);

											$
													.ajax({
														type : "POST",
														enctype : 'multipart/form-data',
														url : "processimage",
														data : data,

														processData : false,
														contentType : false,
														cache : false,
														timeout : 600000,
														success : function(data) {
															if (data.statusMsg == 'OK') {

																$(
																		"#selectcolorresult")
																		.text(
																				data.statusMsg);

																var newSrc = 'processedimages/'
																		+ imageID
																		+ '/processedselectedcolor';
																d = new Date();
																$(
																		'#targetprocessedselectedcolor')
																		.attr(
																				'src',
																				newSrc
																						+ "?"
																						+ d
																								.getTime());

															} else {
																$(
																		"#selectcolorresult")
																		.text(
																				data.statusMsg);
															}
															console
																	.log(
																			"SUCCESS : ",
																			data);
															$(
																	"#selectcolorsubmit")
																	.prop(
																			"disabled",
																			false);

														},
														error : function(e) {

															$(
																	"#selectcolorresult")
																	.text(
																			e.responseText);
															console.log(
																	"ERROR : ",
																	e);
															$(
																	"#selectcolorsubmit")
																	.prop(
																			"disabled",
																			false);

														}
													});

										});

					});
</script>
<link rel="stylesheet" href="css/main.css" type="text/css" />
<link rel="stylesheet" href="css/demos.css" type="text/css" />
<link rel="stylesheet" href="css/jquery.Jcrop.css" type="text/css" />

</head>
<body>

	<div class="container">
		<div class="row">
			<div class="span12">
				<div class="jc-demo-box">

					<div class="page-header">
						<h1>Aabhaa Robata (skm9380@gmail.com) </h1>
					</div>

					<!-- Start of Upload File  -->
					<h1>Upload File</h1>
					<div id="uploadfile">

						<form id="uploadfileform" method="POST" action="/uploadfile"
							enctype="multipart/form-data">
							<input type="file" name="file" /> <label>*Select UserID<input
								type="text" name="userid" />
							</label> <label>*Select EmailID<input type="text" name="emailid" /><BR></BR>
								<input type="submit" value="Submit" id="uploadFileSubmit"></input></label>
							<span id="result" style="color: blue"></span>
						</form>


					</div>
					<!-- End of Upload File  -->





					<!-- Start of Color Selection  -->
					<div id="selectcolor">
						<h1>Select Colors</h1>
						<!-- This is the image we're attaching Jcrop to -->
						<img src="" id="target" alt="[Select Crop]" />
						<p></p>
						<!-- This is the form that our event handler fills -->
						<form id="coords" class="coords"
							action="http://example.com/post.php">

							<label>Select Color1<input type="checkbox" name="color1"
								id="color1" value="1"></label>

							<div class="inline-labels">
								<label>X1 <input type="text" size="4" id="x1" name="x1" /></label>
								<label>Y1 <input type="text" size="4" id="y1" name="y1" /></label>
								<label>X2 <input type="text" size="4" id="x2" name="x2" /></label>
								<label>Y2 <input type="text" size="4" id="y2" name="y2" /></label>
								<label>W <input type="text" size="4" id="w" name="w" /></label>
								<label>H <input type="text" size="4" id="h" name="h" /></label>
							</div>

							<label>Select Color2<input type="checkbox" name="color2"
								id="color2" value="1"></label>

							<div class="inline-labels">
								<label>X1 <input type="text" size="4" id="x11"
									name="x11" /></label> <label>Y1 <input type="text" size="4"
									id="y11" name="y11" /></label> <label>X2 <input type="text"
									size="4" id="x21" name="x21" /></label> <label>Y2 <input
									type="text" size="4" id="y21" name="y21" /></label> <label>W <input
									type="text" size="4" id="w1" name="w1" /></label> <label>H <input
									type="text" size="4" id="h1" name="h1" /></label>
							</div>

							<label>Select Color3<input type="checkbox" name="color3"
								id="color3" value="1"></label>

							<div class="inline-labels">
								<label>X1 <input type="text" size="4" id="x12"
									name="x12" /></label> <label>Y1 <input type="text" size="4"
									id="y12" name="y12" /></label> <label>X2 <input type="text"
									size="4" id="x22" name="x22" /></label> <label>Y2 <input
									type="text" size="4" id="y22" name="y22" /></label> <label>W <input
									type="text" size="4" id="w2" name="w2" /></label> <label>H <input
									type="text" size="4" id="h2" name="h2" /></label>
							</div>

						</form>
						<input type="submit" value="Process" id="selectcolorsubmit"
							class="submit"> <BR /> <span id="selectcolorresult"
							style="color: blue"></span>

					</div>
					<!-- End of Color Selection  -->
					<BR />
					<!-- Start of Processed Image -->
					<img src="" id="targetprocessedselectedcolor"
						alt="[Processed Image]" />
					<!-- End of Processed Image -->
					<div class="clearfix"></div>

				</div>
			</div>
		</div>
	</div>

</body>
</html>

