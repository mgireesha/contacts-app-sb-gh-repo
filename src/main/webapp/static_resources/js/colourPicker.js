function changeRed(value) {
              document.getElementById('valRed').innerHTML = value;
              changeAll();
          }

          function changeGreen(value) {
              document.getElementById('valGreen').innerHTML = value;
              changeAll();
          }

          function changeBlue(value) {
              document.getElementById('valBlue').innerHTML = value;
              changeAll();
          }

          function changeAll() {
              var r = document.getElementById('valRed').innerHTML;
              var g = document.getElementById('valGreen').innerHTML;
              var b = document.getElementById('valBlue').innerHTML;
              document.getElementById('change').style.backgroundColor = "rgb(" + r + "," + g + "," + b + ")";
          }
          
          function selectColour(obj){
                var currentBgClr = $(".navbar").css("background-color");
                var bgColour = $(obj).css("background-color");
                var TableNameFromDb = $("#TableNameFromDb").val();
                var NavBarBgClr = $("#NavBarBgClr").prop("checked");
                var hdrTxtClr = $("#hdrTxtClr").prop("checked");
                if(hdrTxtClr==true || NavBarBgClr==true){
                    $.ajax({
		url : "updateColor?TableNameFromDb="+TableNameFromDb+"&&setColour=YES&&bgColour="+bgColour+"&&NavBarBgClr="+NavBarBgClr+"&&hdrTxtClr="+hdrTxtClr,
		data : true,
		type : "POST",
		error: function(jqXHR){
        		alert("Failed to select colour");
                        $(".navbar").css("background-color",currentBgClr);
                    }
                }).done(function(html) {
                    $(".navbar").css("background-color",bgColour);
                });
                }else{
                    alert("Please select the checkbox");
                }
          }