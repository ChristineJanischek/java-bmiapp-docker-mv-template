import { useMemo, useState } from "react";
import { modules } from "./data/modules";

export default function App() {
  const [activeId, setActiveId] = useState(modules[0].id);

  const active = useMemo(
    () => modules.find((module) => module.id === activeId) ?? modules[0],
    [activeId]
  );

  return (
    <div className="app-shell">
      <header className="hero">
        <div className="hero__badge">OOP Learning Lab</div>
        <h1>
          Moderne Lernplattform fuer Oberstufe
          <br />
          Informatik und Wirtschaftsinformatik
        </h1>
        <p>
          Info, Task und Step-by-Step in einer klaren Lernstrecke.
          Lehrkraefte koennen Inhalte spaeter per Branding und Level steuern.
        </p>
      </header>

      <main className="layout">
        <aside className="module-nav" aria-label="Module Navigation">
          <h2>Module</h2>
          {modules.map((module) => (
            <button
              key={module.id}
              className={`module-nav__item ${
                activeId === module.id ? "is-active" : ""
              }`}
              onClick={() => setActiveId(module.id)}
            >
              <span>{module.id}</span>
              <strong>{module.title}</strong>
              <small>{module.level === "basic" ? "Basis" : "Advanced"}</small>
            </button>
          ))}
        </aside>

        <section className="lesson" aria-live="polite">
          <div className="pill">Fokus: {active.focus}</div>
          <h2>{active.title}</h2>

          <article className="card">
            <h3>Info</h3>
            <p>{active.info}</p>
          </article>

          <article className="card card--task">
            <h3>Task</h3>
            <p>{active.task}</p>
          </article>

          <article className="card">
            <h3>Step by Step</h3>
            <ol>
              {active.steps.map((step) => (
                <li key={step}>{step}</li>
              ))}
            </ol>
          </article>

          <article className="card">
            <div className="code-header">
              <h3>Code Box</h3>
              <span>{active.codeLang.toUpperCase()}</span>
            </div>
            <pre>
              <code>{active.code}</code>
            </pre>
          </article>

          <article className="card card--transfer">
            <h3>Praxis-Transfer</h3>
            <p>{active.transfer}</p>
          </article>
        </section>
      </main>
    </div>
  );
}
