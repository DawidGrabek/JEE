const apiUrl = '/students';
let students = [];
let editingStudentId = null;

document.addEventListener('DOMContentLoaded', function () {
    getAllStudents();
});

function createStudent() {
    const name = document.getElementById('name').value;
    const surname = document.getElementById('surname').value;
    const average = parseFloat(document.getElementById('average').value);

    const newStudent = { name, surname, average };

    fetch(apiUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(newStudent),
    })
    .then(response => response.json())
    .then(() => {
        alert('Student created successfully!');
        getAllStudents();
        clearForm();
    })
    .catch(error => {
        alert('Error creating student: ' + error);
    });
}

function editStudent(id) {
    const student = getStudentById(id);
    document.getElementById('name').value = student.name;
    document.getElementById('surname').value = student.surname;
    document.getElementById('average').value = student.average;

    editingStudentId = id;
    toggleFormButtons(false);
}

function saveStudent() {
    const name = document.getElementById('name').value;
    const surname = document.getElementById('surname').value;
    const average = parseFloat(document.getElementById('average').value);

    const updatedStudent = { name, surname, average };

    fetch(`${apiUrl}/${editingStudentId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(updatedStudent),
    })
    .then(response => response.json())
    .then(() => {
        alert('Student updated successfully!');
        getAllStudents();
        clearForm();
        editingStudentId = null;
        toggleFormButtons(true);
    })
    .catch(error => {
        alert('Error updating student: ' + error);
    });
}

function cancelEdit() {
    clearForm();
    toggleFormButtons(true);
    editingStudentId = null;
}

function getAllStudents() {
    fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
            students = data;
            displayStudents(students);
        })
        .catch(error => {
            alert('Error getting students: ' + error);
        });
}

function deleteStudent(id) {
    if (!confirm('Are you sure you want to delete this student?')) return;

    fetch(`${apiUrl}/${id}`, {
        method: 'DELETE',
    })
    .then(() => {
        alert('Student deleted successfully!');
        if (editingStudentId === id) {
            cancelEdit();
        }
        getAllStudents();
    })
    .catch(error => {
        alert('Error deleting student: ' + error);
    });
}

function displayStudents(students) {
    const studentList = document.getElementById('studentList');
    studentList.innerHTML = `
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Surname</th>
              <th>Average</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody></tbody>
        </table>
    `;

    students.forEach(student => {
        const listItem = document.createElement('tr');
        listItem.innerHTML = `
            <td>${student.id}</td>
            <td>${student.name}</td>
            <td>${student.surname}</td>
            <td>${student.average}</td>
        `;

        const editButton = document.createElement('button');
        editButton.textContent = 'Edit';
        editButton.addEventListener('click', () => editStudent(student.id));

        const deleteButton = document.createElement('button');
        deleteButton.textContent = 'Delete';
        deleteButton.addEventListener('click', () => deleteStudent(student.id));

        listItem.appendChild(editButton);
        listItem.appendChild(deleteButton);
        studentList.querySelector('tbody').appendChild(listItem);
    });
}

function clearForm() {
    document.getElementById('name').value = '';
    document.getElementById('surname').value = '';
    document.getElementById('average').value = '';
}

function toggleFormButtons(showCreate) {
    document.querySelector('button[onclick="createStudent()"]').style.display = showCreate ? 'inline-block' : 'none';
    document.querySelector('button[onclick="saveStudent()"]').style.display = showCreate ? 'none' : 'inline-block';
    document.querySelector('button[onclick="cancelEdit()"]').style.display = showCreate ? 'none' : 'inline-block';
}

function getStudentById(id) {
    return students.find(student => student.id === id);
}
