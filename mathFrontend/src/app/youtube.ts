export function youtubeVideoId(link: string): string {
  const value = (link ?? '').trim();
  if (!value) {
    return '';
  }
  if (/^[\w-]{11}$/.test(value)) {
    return value;
  }

  try {
    const url = new URL(value);
    const host = url.hostname.replace(/^www\./, '');
    if (host === 'youtu.be') {
      return url.pathname.split('/').filter(Boolean)[0] ?? '';
    }

    const queryId = url.searchParams.get('v');
    if (queryId) {
      return queryId;
    }

    const parts = url.pathname.split('/').filter(Boolean);
    const embedAt = parts.indexOf('embed');
    if (embedAt >= 0) {
      return parts[embedAt + 1] ?? '';
    }
    const shortsAt = parts.indexOf('shorts');
    if (shortsAt >= 0) {
      return parts[shortsAt + 1] ?? '';
    }
  } catch {
    return value
      .replace('https://www.youtube.com/watch?v=', '')
      .replace('https://youtu.be/', '')
      .split('&')[0];
  }

  return '';
}
