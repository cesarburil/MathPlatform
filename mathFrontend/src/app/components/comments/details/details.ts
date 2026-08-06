import { Component, inject, input, OnInit, signal } from '@angular/core';
import { Comment } from "../comment/comment";
import { CommentResponse } from '../../../models/CommentResponse';
import { CommentsService } from '../../../services/comments.service';

@Component({
  selector: 'app-details',
  imports: [Comment],
  templateUrl: './details.html',
  styleUrl: './details.scss',
})
export class Details implements OnInit {

  selectedComment = signal<CommentResponse | null>(null);

  commentsService = inject(CommentsService);

  commentId = input<number>(0);

  ngOnInit(): void {
    this.commentsService.getCommentById(this.commentId()).subscribe(result => {
      this.selectedComment.set(result);
    }
    );


  }

}
