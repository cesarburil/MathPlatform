import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SseService {


  connect(url: string): Observable<any> {
    return new Observable(o => {

      const source = new EventSource(url);

      source.onmessage = (event) =>
        o.next(event.data);

      source.onerror = (error) =>
        o.error(error);

      return () => source.close();

    })
  }

}
