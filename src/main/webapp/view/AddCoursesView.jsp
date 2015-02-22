<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>College</title>
		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/default.css" />
		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/form.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/angular.js"></script>
		<script type="text/javascript">
		
			var mPage = angular.module("page", []);
			
			mPage.controller
			(
				"CoursesController",
				function($scope, $http, $window)
				{     
					$scope.Cancel = function()
					{
						$window.location = "${pageContext.request.contextPath}/index";
					}
					
					$scope.Submit = function()
					{
						if($scope.frmCourses.$invalid)
						{
							$scope.frmCourses.txtCoursesName.$setTouched();
							$scope.frmCourses.txtCoursesCredit.$setTouched();
							
							return;
						}
							
						var mParam = 
						{
							coursesname : $scope.courses.name,
							coursescredit : $scope.courses.credit
						};
						
						var strUrlDoAddCoursesService = "${pageContext.request.contextPath}/doaddcoursessvc";
						
						$http.post(strUrlDoAddCoursesService, mParam).success
						(
							function(mResponse)
							{
								if(!mResponse.status)
									return;
								
								alert("Courses has been added");
								$window.location = "${pageContext.request.contextPath}/index";
							}
						);
					}
				}
			);
			
		</script>
	</head>
	<body ng-app="page">
		<div class="place" ng-controller="CoursesController">
			<form name="frmCourses">
				<div class="title">Add Courses</div>
				<div class="component">
					<label for="txtCoursesName" class="label">Name</label>
					<input type="text" id="txtCoursesName" name="txtCoursesName" ng-model="courses.name" required />
					<div ng-show="frmCourses.txtCoursesName.$touched || frmCourses.txtCoursesName.$dirty">
						<div class="error" ng-show="frmCourses.txtCoursesName.$error.required">This field is required</div>
					</div>
				</div>
				<div class="component">
					<label for="txtCoursesCredit" class="label">Credit</label>
					<input type="number" id="txtCoursesCredit" name="txtCoursesCredit" ng-model="courses.credit" required  />
					<div ng-show="frmCourses.txtCoursesCredit.$touched || frmCourses.txtCoursesCredit.$dirty">
						<div class="error" ng-show="frmCourses.txtCoursesCredit.$error.required">This field is required</div>
						<div class="error" ng-show="frmCourses.txtCoursesCredit.$error.number">Wrong credit format</div>
					</div>
				</div>
				<div class="action">
					<button type="button" class="button-link" ng-click="Cancel()">Cancel</button>
					<button type="button" class="button-Submit" ng-click="Submit()">Submit</button>
				</div>
			</form>
		</div>
	</body>
</html>