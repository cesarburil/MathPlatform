import { Component, input } from '@angular/core';
import { CommentResponse } from '../../../models/CommentResponse';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-comment',
  imports: [RouterLink],
  templateUrl: './comment.html',
  styleUrl: './comment.scss',
})
export class Comment {

  comment = input<CommentResponse | null>(null);
  
}
