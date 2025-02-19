const body = document.querySelector("body"),
  sidebar = body.querySelector("nav"),
  toggle = body.querySelector(".toggle"),
  modeSwitch = body.querySelector(".toggle-switch"),
  modeText = body.querySelector(".mode-text");

toggle.addEventListener("click", () => {
  sidebar.classList.toggle("close");
});


modeSwitch.addEventListener("click", () => {
  body.classList.toggle("dark");

  if (body.classList.contains("dark")) {
    modeText.innerText = "Mode claire";
  } else {
    modeText.innerText = "Mode sombre";
  }
});


// Données pour le graphique (remplace par des données dynamiques si nécessaire)
const appointmentsData = {
    labels: ['Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi', 'Dimanche'],
    datasets: [{
        label: 'Rendez-vous par jour',
        data: [12, 19, 3, 5, 2, 3, 7],
        backgroundColor: 'rgba(52, 152, 219, 0.2)',
        borderColor: 'rgba(52, 152, 219, 1)',
        borderWidth: 2
    }]
};

// Configuration du graphique
const config = {
    type: 'line',
    data: appointmentsData,
    options: {
        responsive: true,
        plugins: {
            legend: {
                position: 'top',
            },
            title: {
                display: true,
                text: 'Rendez-vous par jour'
            }
        }
    }
};

// Création du graphique
const appointmentsChart = new Chart(
    document.getElementById('appointmentsChart'),
    config
);