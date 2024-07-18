import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubscribedVideosComponent } from './subscribed-videos.component';

describe('SubscribedVideosComponent', () => {
  let component: SubscribedVideosComponent;
  let fixture: ComponentFixture<SubscribedVideosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SubscribedVideosComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SubscribedVideosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
