const recoverForm = document.getElementById('recoverForm');

const emailOption = document.getElementById('option1');
const passwordOption = document.getElementById('option2');

//  이거 선생님께 물어보기 왜 [0]을 입력해야 하는지
const emailContainer = document.getElementsByClassName("email-container")[0];
const passwordContainer = document.getElementsByClassName("password-container")[0];

emailOption.addEventListener('click', function (){
    emailContainer.style.display='flex';
    passwordContainer.style.display='none';
    passwordOption.style.opacity='0.5';
});

passwordOption.addEventListener('click',function (){
   passwordContainer.style.display='block';
   emailContainer.style.display='none';
   emailOption.style.opacity= '0.5';
});






