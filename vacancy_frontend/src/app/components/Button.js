import './style.css'

export function Button({ children, onClick }) {
  return (
    <button className="custom-button" onClick={onClick}>
      {children}
    </button>
  );
}