import { HttpErrorResponse } from '@angular/common/http';

const KNOWN: [RegExp, string][] = [
  [/bad credentials/i, 'Usuário ou senha incorretos.'],
  [/invalid username or password/i, 'Usuário ou senha incorretos.'],
  [/user already exists/i, 'Esse usuário já está cadastrado.'],
  [/payment/i, 'Não foi possível processar o pagamento.'],
];

function readRawMessage(error: HttpErrorResponse): string {
  const body = error.error;

  if (typeof body === 'string') {
    const trimmed = body.trim();
    if (!trimmed) {
      return '';
    }
    try {
      const parsed = JSON.parse(trimmed) as { message?: unknown };
      if (typeof parsed?.message === 'string') {
        return parsed.message;
      }
    } catch {
      return trimmed;
    }
    return trimmed;
  }

  if (body && typeof body === 'object' && 'message' in body) {
    const message = (body as { message?: unknown }).message;
    if (typeof message === 'string') {
      return message;
    }
  }

  return error.statusText ?? '';
}

function mapKnown(raw: string): string | null {
  for (const [pattern, text] of KNOWN) {
    if (pattern.test(raw)) {
      return text;
    }
  }
  return null;
}

export function friendlyHttpMessage(error: HttpErrorResponse, url = ''): string {
  if (error.status === 0) {
    return 'Não foi possível conectar. Verifique a internet e tente de novo.';
  }

  const raw = readRawMessage(error);
  const known = mapKnown(raw);
  if (known) {
    return known;
  }

  const isLogin = url.includes('/login') || url.includes('/register');
  if (error.status === 401) {
    return isLogin
      ? 'Usuário ou senha incorretos.'
      : 'Sua sessão expirou. Entre novamente.';
  }

  if (error.status === 403) {
    return 'Você não tem permissão para isso.';
  }

  if (error.status >= 500) {
    return 'Algo deu errado no servidor. Tente de novo em instantes.';
  }

  return 'Não foi possível concluir isso agora. Tente de novo.';
}
