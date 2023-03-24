showInput();
getTeachers();

function showInput() {
  let el = document.querySelectorAll(".input");
  for (let e of el) {
    e.classList.remove("invisible");
  }

  el = document.querySelectorAll(".edit");
  for (let e of el) {
    e.classList.add("invisible");
  }
}

function showEdit() {
  let el = document.querySelectorAll(".input");
  for (let e of el) {
    e.classList.add("invisible");
  }

  el = document.querySelectorAll(".edit");
  for (let e of el) {
    e.classList.remove("invisible");
  }
}

function getTeachers() {
  fetch("../admin/teachers")
    .then((response) => {
      return response.json();
    })
    .then((result) => {
      let tbody = "";
      result.data.forEach((teacher) => {
        let html = `
<tr data-no="${teacher.no}" onclick="getTeacher(event)">
   <td>${teacher.no}</td> 
   <td>${teacher.name}</td> 
   <td>${teacher.tel}</td>
   <td>${getLevelTitle(teacher.degree)}</td>
   <td>${teacher.major}</td>
   <td>${teacher.wage}</td>
</tr>
`;
        tbody += html;
      });
      document.querySelector("#teacher-table > tbody").innerHTML = tbody;
    });
}

function getLevelTitle(degree) {
  switch (degree) {
    case 1:
      return "고졸";
    case 2:
      return "전문학사";
    case 3:
      return "학사";
    case 4:
      return "석사";
    case 5:
      return "박사";
    default:
      return "기타";
  }
}

function getTeacher(e) {
  let no = e.currentTarget.getAttribute("data-no");

  fetch("../admin/teachers/" + no)
    .then((response) => {
      return response.json();
    })
    .then((result) => {
      if (result.status == "failure") {
        alert("강사를 조회할 수 없습니다.");
        return;
      }

      let teacher = result.data;
      document.querySelector("#f-no").value = teacher.no;
      document.querySelector("#f-name").value = teacher.name;
      document.querySelector("#f-email").value = teacher.email;
      document.querySelector("#f-tel").value = teacher.tel;
      document.querySelector("#f-degree").value = teacher.degree;
      document.querySelector("#f-school").value = teacher.school;
      document.querySelector("#f-major").value = teacher.major;
      document.querySelector("#f-wage").value = teacher.wage;
      document.querySelector("#f-createdDate").innerHTML = teacher.createdDate;

      showEdit();
    });
}

document.querySelector("#btn-insert").onclick = () => {
  const form = document.querySelector("#teacher-form");
  const formData = new FormData(form);

  let json = JSON.stringify(Object.fromEntries(formData));

  fetch("../admin/teachers", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: json,
  })
    .then((response) => {
      return response.json();
    })
    .then((result) => {
      if (result.status == "success") {
        location.reload();
      } else {
        alert("입력 실패!");
        console.log(result.data);
      }
    })
    .catch((exception) => {
      alert("입력 중 오류 발생!");
      console.log(exception);
    });
};

document.querySelector("#btn-update").onclick = () => {
  const form = document.querySelector("#teacher-form");
  const formData = new FormData(form);

  // FormData ==> Query String
  // 방법1)
  //let qs = [...formData.entries()].map(x => `${encodeURIComponent(x[0])}=${encodeURIComponent(x[1])}`).join('&');
  // 방법2)
  //let qs = new URLSearchParams(formData).toString();
  //console.log(qs);

  let json = JSON.stringify(Object.fromEntries(formData));
  //console.log(json);

  fetch("../admin/teachers/" + document.querySelector("#f-no").value, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      //"Content-Type": "application/x-www-form-urlencoded"
    },
    //body: formData
    body: json,
    //body: qs
  })
    .then((response) => {
      return response.json();
    })
    .then((result) => {
      if (result.status == "success") {
        alert("변경 했습니다.");
        location.reload();
      } else {
        alert("변경 실패!");
        console.log(result.data);
      }
    })
    .catch((exception) => {
      alert("변경 중 오류 발생!");
      console.log(exception);
    });
};

document.querySelector("#btn-delete").onclick = () => {
  fetch("../admin/teachers/" + document.querySelector("#f-no").value, {
    method: "DELETE",
  })
    .then((response) => {
      return response.json();
    })
    .then((result) => {
      if (result.status == "success") {
        location.reload();
      } else {
        alert("파일 삭제 실패!");
      }
    })
    .catch((exception) => {
      alert("파일 삭제 중 오류 발생!");
      console.log(exception);
    });
};

document.querySelector("#btn-cancel").onclick = () => {
  showInput();
};

// entries ==> query string
function toQueryStringFromEntries(entries) {
  let qs = "";
  for (let [key, value] of entries) {
    if (qs.length > 0) {
      qs += "&";
    }
    qs += encodeURIComponent(key) + "=" + encodeURIComponent(value);
  }
  return qs;
}

function toQueryStringFromEntries2(entries) {
  let arr = [];
  for (let entry of entries) {
    arr.push(entry);
  }

  //console.log(arr);

  let arr2 = arr.map(
    (x) => `${encodeURIComponent(x[0])}=${encodeURIComponent(x[1])}`
  );
  //console.log(arr2);

  let str = arr2.join("&");
  //console.log(str);

  return str;
}

function toQueryStringFromEntries3(entries) {
  let arr = [...entries];

  //console.log(arr);

  let arr2 = arr.map(
    (x) => `${encodeURIComponent(x[0])}=${encodeURIComponent(x[1])}`
  );
  //console.log(arr2);

  let str = arr2.join("&");
  //console.log(str);

  return str;
}
