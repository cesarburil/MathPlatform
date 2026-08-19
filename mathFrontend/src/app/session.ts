export function readJwtRole(token: string): string | null {
  try {
    const part = token.split('.')[1];
    if (!part) {
      return null;
    }
    const json = JSON.parse(atob(part.replace(/-/g, '+').replace(/_/g, '/'))) as {
      role?: unknown;
    };
    return typeof json.role === 'string' ? json.role : 'USER';
  } catch {
    return 'USER';
  }
}
