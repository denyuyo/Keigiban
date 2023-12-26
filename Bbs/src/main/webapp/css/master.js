function changeColorByName(color){
			document.getElementsByName("username")[0].style.color = color;
			document.getElementsByName("email")[0].style.color = color;
			document.getElementsByName("title")[0].style.color = color;
			document.getElementsByName("text")[0].style.color = color;
}

function chkField1(){
				if(document.Form1.userID.value == "" || document.Form1.password.value == ""){
					alert("必須項目が入力されていません。");
					return false;
				}else {
					alert("ID:" + document.Form1.userID.value + " password:" +
							document.Form1.password.value);
					return true;
				}
}

  
   let form = document.getElementById('form');
  
  // 第三引数にtrueを指定する
  form.addEventListener('focus', (e) => {
    e.target.style.background = 'yellow';
  }, true);

  // 同じく
  form.addEventListener('blur', (e) => {
    e.target.style.background = "";
  }, true);
  
  
  function changeColorById(id,color){
	  document.getElementById(id).style.backgroundColor = color;

}