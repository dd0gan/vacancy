import './style.css'

export function Col({ children, size }) {
  return <div className={`col col-${size}`}>{children}</div>;
}