let currentTheme = getTheme();

document.addEventListener("DOMContentLoaded", () => {
    applyTheme(currentTheme);
    const themeBtn = document.querySelector("#theme_change_button");
    if (themeBtn) {
        themeBtn.addEventListener("click", toggleTheme);
    }
});

function toggleTheme() {
    let newTheme = currentTheme === "dark" ? "light" : "dark";
    applyTheme(newTheme);
    currentTheme = newTheme;
    setTheme(newTheme);
}

function applyTheme(theme) {
    const html = document.querySelector("html");
    html.classList.remove("light", "dark");
    html.classList.add(theme);
    const themeBtnSpan = document.querySelector("#theme_change_button span");
    if (themeBtnSpan) {
        themeBtnSpan.textContent = theme === "dark" ? "Light" : "Dark";
    }
}

function setTheme(theme) {
    localStorage.setItem("theme", theme);
}

function getTheme() {
    let theme = localStorage.getItem("theme");
    return theme === "dark" ? "dark" : "light";
}